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
  def known?(task_name)
    # http://stackoverflow.com/questions/1986386/check-if-a-value-exists-in-an-array-in-ruby
    @tasks.include?(task_name)
  end
  def print(config)
    @tasks.each do | line |
      config.putsq line
    end
  end
end

class TimeLogEntry
  # https://ruby-doc.org/core-2.4.0/Comparable.html
  include Comparable
  def <=>(other)
    @commit_id <=> other.commit_id
  end

  attr_reader :commit_id, :task_name, :minutes

  def initialize(config, entry_string, known_tasks)
    # 
    # https://ruby-doc.org/core-2.2.0/String.html#method-i-split
    entry_array = entry_string.split
    @commit_id = entry_array[0]
    if entry_array[1] != "time" then
      # handle error situations
      # http://blog.honeybadger.io/how-to-exit-a-ruby-program/
      # http://phrogz.net/programmingruby/tut_exceptions.html
      # https://ruby-doc.org/core-2.4.0/Kernel.html for catch and throw
      config.putsq "WARNING: missing time keyword in first line of time log entry"
      config.putsq entry_string
      config.putsq "WARNING: was probably on another line by mistake, fix it according to practice protocol"
      throw :skip_entry
    end
    if not known_tasks.known?(entry_array[2]) then
      config.putsq "WARNING: skipping this log entry because unknown task " + entry_array[2]
      config.putsq entry_string
      throw :skip_entry
    end
    @task_name = entry_array[2]
    @minutes = entry_array[3].to_i
    if @minutes == 0 then
      config.putsq "WARNING: skipping this log entry because bad integer value or 0"
      config.putsq entry_string
      throw :skip_entry
    end
  end
  def threshold!(config)
    upper_bound = config.max_entry_time
    if upper_bound < 0 then
      config.puterr "ERROR: negative upper_bound " + (upper_bound.to_s)
      throw :internal_error
    end
    if @minutes > upper_bound then
       config.putsq "WARNING: minutes reduced to "+ (upper_bound.to_s) + " from " + (@minutes.to_s)
       self.print(config)
       @minutes = upper_bound
    end
    if @minutes < -upper_bound then
       config.putsq "WARNING: minutes raise to "+ (upper_bound.to_s) + " from " + (@minutes.to_s)
       self.print(config)
       @minutes = -upper_bound
    end
    self
  end
  def print(config)
    config.putsq @commit_id + " " + @task_name + " " + (@minutes.to_s)
  end
end

class TimeLog
  def initialize(config, time_log_source, known_tasks = nil)
    # http://stackoverflow.com/questions/3958735/in-ruby-is-there-a-way-to-overload-the-initialize-constructor
    if time_log_source.class == Array then
      @log = time_log_source
      return
    end
    # otherwise, assume time_log_source is a string
    @log = Array.new
    time_log_source.each_line do | line |
      catch :skip_entry do
        @log.push(TimeLogEntry.new(config, line, known_tasks))
      end
    end
  end
  def include?(entry)
    @log.include?(entry)
  end
  def remove(config, old_log)
    log = @log.keep_if do | entry |
      ! old_log.include?(entry)
    end   
    TimeLog.new(config, log)
  end
  def threshold!(config)
    log = @log.map do | entry |
      entry.threshold!(config)
    end   
    TimeLog.new(config, log)
  end
  def each(&block)
    @log.each do | entry |
      block.call(entry)
    end
  end
  def print(config)
    @log.each do | entry |
       entry.print(config)
    end
  end
end

class OldTimeLog 
  def initialize(config, old_time_log_file_name, known_tasks)
    @time_log = TimeLog.new(config, IO.read(old_time_log_file_name), known_tasks)
  end
  def print(config)
    @time_log.print(config)
  end
  def include?(entry)
    @time_log.include?(entry)
  end
end

class NewTimeLog 
  def initialize(config, known_tasks)
    #use of back quotes in Ruby: http://ruby-doc.org/core-2.4.0/Kernel.html#method-i-60
    if config.git_from_file then
      stdout = IO.read("windowsCurrentLog")
    else
      stdout = `git --no-pager log --pretty=oneline --grep "^time "`
    end
    # matches on lines where time is not in the first line, sigh!
    @time_log = TimeLog.new(config, stdout, known_tasks)
  end
  def print(config)
    @time_log.print(config)
  end
  def report(config, old_log)
    time_log = @time_log.remove(config, old_log)
    time_log = time_log.threshold!(config)
    TimeLogReport.new(time_log).analyze
  end
end

class TimeLogReport
  def initialize(time_log)
    @time_log = time_log
    @report = Hash.new
    @total = 0.0
    @javascript_total = 0.0
    @ruby_total = 0.0
    @haskell_total = 0.0
    # hash tables explained https://ruby-doc.org/core-2.2.0/Hash.html
  end
  def analyze()
    @time_log.each do | entry |
       task_name = entry.task_name
       # http://stackoverflow.com/questions/5502761/why-is-division-in-ruby-returning-an-integer-instead-of-decimal-value
       minutes = entry.minutes.to_f
       if @report.has_key?(task_name) then
         @report[task_name] = @report[task_name] + minutes
       else
         @report[task_name] = minutes
       end
       @total += minutes
       # http://stackoverflow.com/questions/18463727/how-can-i-check-for-first-letters-in-string-in-ruby
       # https://ruby-doc.org/core-2.2.0/String.html#method-i-start_with-3F
       # http://stackoverflow.com/questions/4500504/does-it-matter-if-a-conditional-statement-comes-before-or-after-the-expression
       @javascript_total += minutes if task_name.start_with?("j")
       @ruby_total += minutes if task_name.start_with?("r")
       @haskell_total += minutes if task_name.start_with?("h")
    end
    self
  end
  def print(config)
    if config.report_type == :mark then
      printMark(config)
    else
      printGeneral(config)
    end
  end
  def printMark(config)
    puts "On " + config.date + " marking for previous week"
    total = @total
    javascript_total = @javascript_total
    ruby_total = @ruby_total
    haskell_total = @haskell_total
    if total > config.max_week_time then
       javascript_total = javascript_total * config.max_week_time / total
       ruby_total = ruby_total * config.max_week_time / total
       haskell_total = haskell_total * config.max_week_time / total
       total = config.max_week_time
    end
    # https://ruby-doc.org/core-2.4.0/Float.html#method-i-round
    javascript_total = javascript_total.round(config.float_precision)
    ruby_total = ruby_total.round(config.float_precision)
    haskell_total = haskell_total.round(config.float_precision)
    total = ((10.0 * total)/config.max_week_time).round(config.float_precision)
    puts "javascript practice time for previous week " + (javascript_total.to_s)
    puts "ruby practice time for previous week " + (ruby_total.to_s)
    puts "haskell practice time for previous week " + (haskell_total.to_s)
    OldMarks.new(config).report(javascript_total, ruby_total, haskell_total)
    puts "mark for previous week " + (total.to_s) + " (out of 10)"
  end
  def printGeneral(config)
    config.putsq "----- Time Log Report"
    config.putsq "------- By Task"
    @report.each do | task, minutes |
      puts "total time spent on task " + task + " was " + (minutes.to_s)
    end
    config.putsq "total time on all tasks according to this log was " + (@total.to_s)
    total = @total
    if total > config.max_week_time then
       config.putsq "Note: time reported in excess of 180 minutes does not count toward practice"
       total = config.max_week_time
    end
    total_percent = ((100.0 * total)/180.0).round(config.float_precision)
    puts "the percentage of weekly required practice time spent so far is " + (total_percent.to_s) + "% (target is 100.0%)"
  end
end

class OldMarks
    # created to support a report of practice work on a per language basis 
    # so far this semester, invoked:
    #  OldMarks.new(config).report(javascript_total, ruby_total, haskell_total)
    
    def initialize(config)
       @skip_report = ! (config.old_marks_file)
       return if @skip_report
       old_marks_file = config.old_marks_file
       old_marks_file_string = IO.read(old_marks_file)
       @old_marks_file_contents = Array.new
       old_marks_file_string.each_line do | line |
         @old_marks_file_contents.push(line.delete("\n"))
       end
    end
    def report(current_javascript_total, current_ruby_total, current_haskell_total)
       return if @skip_report
       @old_marks_file_contents.each do | line |
          puts "@@ " + line
       end
       puts "@@ current_javascript_total=" + (current_javascript_total.to_s)
       puts "@@ current_ruby_total=" + (current_ruby_total.to_s)
       puts "@@ current_haskell_total=" + (current_haskell_total.to_s)
    end
end


class SummaryConfig
  attr_accessor :date, :report_type, :quiet, :error_out, :max_entry_time, :max_week_time, :git_from_file, :float_precision, :old_marks_file
  def initialize(arg_list)
    if arg_list.include?("mark") then
      @report_type = :mark
      @quiet = true
    else
      @report_type = :general
    end
    if arg_list.any? { |value| /^date=/ =~ value } then
      # https://www.ruby-forum.com/topic/171339 
      date_arg = arg_list.find { |value| /^date=/ =~ value }
      @date = date_arg.sub("date=","")
    else
      @date = "unknown"
    end
    if arg_list.any? { |value| /^oldmarks=/ =~ value } then
      oldmarks_arg = arg_list.find { |value| /^oldmarks=/ =~ value }
      @old_marks_file = oldmarks_arg.sub("oldmarks=","")
    else
      @old_marks_file = false
    end
    if arg_list.include?("quiet") then
      @quiet = true
    else
      # http://www.rubyinside.com/what-rubys-double-pipe-or-equals-really-does-5488.html
      @quiet ||= false
    end
    if arg_list.include?("gitfromfile") then
      @git_from_file = true
    else
      @git_from_file = false
    end
    @error_out = STDERR
    @max_entry_time = 60.0
    @max_week_time = 180.0
    @float_precision = 3
  end
  def putsq(message)
    puts message unless @quiet
  end
  def puterr(message)
    @error_out.puts message
  end
end

config = SummaryConfig.new(ARGV)
# http://alvinalexander.com/blog/post/ruby/how-read-command-line-arguments-args-script-program

config.putsq "----- Beginning to process log file"
config.putsq "----- First read known task list"
known_tasks = KnownTasks.new("knowntasks")
known_tasks.print(config)
config.putsq "----- Fetching list of already processed practice times"
already_processed = OldTimeLog.new(config, "lastMarkedTimeLog", known_tasks)
config.putsq "-------- Already processed practice times "
already_processed.print(config)
config.putsq "----- Fetching current practice times"
to_be_analyzed = NewTimeLog.new(config, known_tasks)
config.putsq "-------- Current practice times "
to_be_analyzed.print(config)
config.putsq "----- Summary Report"
to_be_analyzed.report(config, already_processed).print(config)

