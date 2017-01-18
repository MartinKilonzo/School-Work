require 'open3'
# see NewTimeLog#initialize

class KnownTasks
  def initialize(known_task_file_name)
   # need to read file into memory
   # https://mauricio.github.io/2014/08/03/quick-tips-for-doing-io-with-ruby.html
   # http://ruby-doc.org/core-2.0.0/IO.html
   @tasks = Array.new
   IO.read(known_task_file_name).each_line do | line |
      @tasks.push(line.delete("\n"))
   end
   # each_line defined in https://ruby-doc.org/core-2.2.0/String.html
   # array stuff in https://ruby-doc.org/core-2.2.0/Array.html
   # also useful was http://stackoverflow.com/questions/17132262/removing-line-breaks-in-ruby
  end
  def known(task_name)
    # http://stackoverflow.com/questions/1986386/check-if-a-value-exists-in-an-array-in-ruby
    @tasks.include?(task_name)
  end
  def print()
    @tasks.each do | line |
      puts line
    end
  end
end

class TimeLogEntry
  # https://ruby-doc.org/core-2.4.0/Comparable.html
  include Comparable
  def <=>(other)
    @commit_id <=> other.commit_id
  end
  def commit_id()
    return @commit_id
  end
  def task_name()
    return @task_name
  end
  def minutes()
    return @minutes
  end
  def initialize(entry_string, known_tasks)
    # 
    # https://ruby-doc.org/core-2.2.0/String.html#method-i-split
    entry_array = entry_string.split
    @commit_id = entry_array[0]
    if entry_array[1] != "time" then
      # handle error situations
      # http://blog.honeybadger.io/how-to-exit-a-ruby-program/
      # http://phrogz.net/programmingruby/tut_exceptions.html
      # https://ruby-doc.org/core-2.4.0/Kernel.html for catch and throw
      STDERR.puts "ERROR: missing time keyword in time log entry"
      STDERR.puts entry_string
      throw :internal_error
    end
    if not known_tasks.known(entry_array[2]) then
      STDERR.puts "WARNING: skipping this log entry because unknown task " + entry_array[2]
      STDERR.puts entry_string
      throw :skip_entry
    end
    @task_name = entry_array[2]
    @minutes = entry_array[3].to_i
    if @minutes == 0 then
      STDERR.puts "WARNING: skipping this log entry because bad integer value or 0"
      STDERR.puts entry_string
      throw :skip_entry
    end
  end
  def thresholded(upper_bound)
    if upper_bound < 0 then
      STDERR.puts "ERROR: negative upper_bound " + (upper_bound.to_s)
      throw :internal_error
    end
    if @minutes >= upper_bound then
       STDERR.puts "WARNING: minutes reduced to "+ (upper_bound.to_s) + " from " + (@minutes.to_s)
       self.print
       @minutes = upper_bound
    end
    if @minutes <= -upper_bound then
       STDERR.puts "WARNING: minutes raise to "+ (upper_bound.to_s) + " from " + (@minutes.to_s)
       self.print
       @minutes = -upper_bound
    end
    self
  end
  def print()
    puts @commit_id + " " + @task_name + " " + (@minutes.to_s)
  end
end

class TimeLog
  def initialize(time_log_source, known_tasks = nil)
    # http://stackoverflow.com/questions/3958735/in-ruby-is-there-a-way-to-overload-the-initialize-constructor
    if time_log_source.class == Array then
      @log = time_log_source
      return
    end
    # otherwise, assume time_log_source is a string
    @log = Array.new
    time_log_source.each_line do | line |
      catch :skip_entry do
        @log.push(TimeLogEntry.new(line, known_tasks))
      end
    end
  end
  def include?(entry)
    @log.include?(entry)
  end
  def remove(old_log)
    log = @log.keep_if do | entry |
      ! old_log.include?(entry)
    end   
    TimeLog.new(log, nil)
  end
  def threshold(bound)
    log = @log.map do | entry |
      entry.thresholded(bound)
    end   
    TimeLog.new(log, nil)
  end
  def each(&block)
    @log.each do | entry |
      block.call(entry)
    end
  end
  def print()
    @log.each do | entry |
       entry.print
    end
  end
end

class OldTimeLog 
  def initialize(old_time_log_file_name, known_tasks)
    @time_log = TimeLog.new(IO.read(old_time_log_file_name), known_tasks)
  end
  def print()
    @time_log.print    
  end
  def include?(entry)
    @time_log.include?(entry)
  end
end

class NewTimeLog 
  def initialize(known_tasks)
    # need to read the log into Ruby
    # http://blog.honeybadger.io/capturing-stdout-stderr-from-shell-commands-via-ruby/
    # https://ruby-doc.org/stdlib-2.2.1/libdoc/open3/rdoc/Open3.html
    stdout, stderr, status = Open3.capture3('git --no-pager log --pretty=oneline --grep "^time"')
    @time_log = TimeLog.new(stdout, known_tasks)
  end
  def print()
    @time_log.print
  end
  def report(old_log)
    @time_log = @time_log.remove(old_log)
    @time_log = @time_log.threshold(60)
    TimeLogReport.new(@time_log).analyze
  end
end

class TimeLogReport
  def initialize(time_log)
    @time_log = time_log
    @report = Hash.new
    @total = 0
    # hash tables explained https://ruby-doc.org/core-2.2.0/Hash.html
  end
  def analyze()
    @time_log.each do | entry |
       task_name = entry.task_name
       minutes = entry.minutes
       if @report.has_key?(task_name) then
         @report[task_name] = @report[task_name] + minutes
       else
         @report[task_name] = minutes
       end
       @total = @total + minutes
    end
    self
  end
  def print()
    puts "----- Time Log Report"
    puts "------- By Task"
    @report.each do | task, minutes |
      puts "total time spent on task " + task + " was " + (minutes.to_s)
    end
    puts "total time on all tasks according to this log was " + (@total.to_s)
    if @total > 180 then
       puts "Note: time reported in excess of 180 minutes does not count toward practice"
       @total = 180
    end
    puts "the percentage of weekly required practice time spent so far is " + (((100.0 * @total)/180.0).to_s) + "% (target is 100.0%)"
  end
end

puts "----- Beginning to process log file"
puts "----- First read known task list"
known_tasks = KnownTasks.new("knowntasks");
known_tasks.print
puts "----- Fetching list of already processed practice times"
already_processed = OldTimeLog.new("lastMarkedTimeLog", known_tasks);
puts "-------- Already processed practice times "
already_processed.print
puts "----- Fetching current practice times"
to_be_analyzed = NewTimeLog.new(known_tasks);
puts "-------- Current practice times "
to_be_analyzed.print
puts "----- Summary Report"
to_be_analyzed.report(already_processed).print

