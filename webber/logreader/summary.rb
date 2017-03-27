class KnownTasks
  def initialize(known_task_file_name)
   # need to read file into memory
   # https://mauricio.github.io/2014/08/03/quick-tips-for-doing-io-with-ruby.html
   # http://ruby-doc.org/core-2.0.0/IO.html
   @tasks = Array.new
   input_string = IO.read(known_task_file_name)
   input_string.each_line { | line | @tasks.push(line.chomp()) }
#   IO.read(known_task_file_name).each_line do | line |
#      @tasks.push(line.delete("\n"))
#   end
   # each_line defined in https://ruby-doc.org/core-2.2.0/String.html
   # array stuff in https://ruby-doc.org/core-2.2.0/Array.html
   # also useful was http://stackoverflow.com/questions/17132262/removing-line-breaks-in-ruby
   # https://ruby-doc.org/core-2.2.0/String.html for chomp
  end
  def known?(task_name)
    # http://stackoverflow.com/questions/1986386/check-if-a-value-exists-in-an-array-in-ruby
    @tasks.include?(task_name)
  end
  def print(config)
    @tasks.each { | line | config.putsq line }
  end
end

class TimeLogEntry
#  # https://ruby-doc.org/core-2.4.0/Comparable.html
#  include Comparable
#  def <=>(other)
#    @commit_id <=> other.commit_id
#  end

  def equal_ids(other)
    @commit_id == other.commit_id
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
    @log.each do | log_entry |
      return true if log_entry.equal_ids(entry)
    end
    return false
  end
  def remove(config, old_log)
    log = @log.select do | entry |
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
    puts "@+@ javascript time for week found before rules applied " + (@javascript_total.to_s)
    puts "@+@ ruby time for week found before rules applied " + (@ruby_total.to_s)
    puts "@+@ haskell time for week found before rules applied " + (@haskell_total.to_s)
    self
  end
  def print(config)
    if config.report_type == :mark then
      print_mark(config)
    else
      print_general(config)
    end
  end
  def print_mark(config)
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
    old_marks = OldMarks.new(config)
    puts "@+@ time from prior weeks on javascript " + (old_marks.sum_up("javascript").to_s)
    puts "@+@ time from prior weeks on ruby " + (old_marks.sum_up("ruby").to_s)
    puts "@+@ time from prior weeks on haskell " + (old_marks.sum_up("haskell").to_s)
    puts "@+@ time from prior weeks on discretionary " + (old_marks.sum_up("discretionary").to_s)

    # http://stackoverflow.com/questions/1961020/return-two-and-more-values-from-a-method
    javascript_total, ruby_total, haskell_total, discretionary_total = old_marks.practice_limits(javascript_total, ruby_total, haskell_total)
    total = javascript_total + ruby_total + haskell_total + discretionary_total
    sum_javascript_total, sum_ruby_total, sum_haskell_total, sum_discretionary_total = old_marks.sum_practice(javascript_total, ruby_total, haskell_total, discretionary_total)
    # https://ruby-doc.org/core-2.4.0/Float.html#method-i-round
    javascript_total = config.to_f(javascript_total)
    ruby_total = config.to_f(ruby_total)
    haskell_total = config.to_f(haskell_total)
    discretionary_total = config.to_f(discretionary_total)
    mark = config.to_f((10.0 * total)/config.max_week_time)
    puts "javascript practice time for previous week " + (javascript_total.to_s)
    puts "ruby practice time for previous week " + (ruby_total.to_s)
    puts "haskell practice time for previous week " + (haskell_total.to_s)
    puts "discretionary practice time for previous week " + (discretionary_total.to_s)
    old_marks.report(sum_javascript_total, sum_ruby_total, sum_haskell_total, sum_discretionary_total)
    puts "mark for previous week " + (mark.to_s) + " (out of 10)" if config.report_type == :mark
  end
  def print_general(config)
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
    self.print_mark(config)
    total_percent = config.to_f((100.0 * total)/180.0)
    puts "the percentage of weekly required practice time spent so far is " + (total_percent.to_s) + "% (target is 100.0%)"
  end
end

class OldMarks
    # created to support a report of practice work on a per language basis 
    # so far this semester
    
    def initialize(config)
       @config = config
       @skip_report = ! (config.old_marks_file)
       return if @skip_report
       old_marks_file = config.old_marks_file
       old_marks_file_string = IO.read(old_marks_file)
       @old_marks_file_contents = Array.new
       old_marks_file_string.each_line do | line |
         @old_marks_file_contents.push(line.chomp())
       end
    end

    def sum_up(language)
       # in the previous marks result, sum up the times allotted to language
       # in lines like
       #    discretionary practice time for previous week 0.0
       values = @old_marks_file_contents.map do | line |
          words = line.split
          # http://stackoverflow.com/questions/7156955/whats-the-difference-between-equal-eql-and
          # http://stackoverflow.com/questions/1710369/most-concise-way-to-test-string-equality-not-object-equality-for-ruby-strings
          if words[0] == language then
             words[6].to_f
          else
             0.0
          end
       end
       # http://stackoverflow.com/questions/1538789/how-to-sum-array-of-numbers-in-ruby
       sum = values.inject(0.0, :+)
    end

    def discretionary_used()
       mct = @config.max_category_time
       javascript_total = sum_up("javascript")
       ruby_total = sum_up("ruby")
       haskell_total = sum_up("haskell")
       discretionary = sum_up("discretionary")
       discretionary += javascript_total - mct if javascript_total > mct
       discretionary += ruby_total - mct if ruby_total > mct
       discretionary += haskell_total - mct if haskell_total > mct
       discretionary = mct if discretionary > mct
       # puts "@+@ discretionary used "+ (discretionary.to_s)
       return discretionary
    end

    def discretionary_limit_update(discretionary_limit, language_total, name)
       # puts "@+@ discretionary_limit_update incoming " + name + " " + (discretionary_limit.to_s) + "," + (language_total.to_s)
       mct = [@config.max_category_time - sum_up(name), 0.0].max
       if language_total > mct then
         if discretionary_limit >= (language_total - mct) then
           discretionary_limit = discretionary_limit - (language_total - mct)
           language_total = mct
         else
           @config.puterr("WARNING: discretionary limit exceeded, so no more practice time on " + name + " counts as practice time; check your weekly times to see if this has impacted them as excess time beyond discretionary limit does not count as practice time.")
           discretionary_limit = 0.0
           language_total = mct
         end
       end
       # puts "@+@ discretionary_limit_update outgoing " + name + " " + (discretionary_limit.to_s) + "," + (language_total.to_s)
       return discretionary_limit, language_total
    end

    def practice_limits(javascript_total, ruby_total, haskell_total)
       # puts "@+@ practice_limits incoming " + (javascript_total.to_s) + "," + ( ruby_total.to_s) + "," + (haskell_total.to_s)
       mct = @config.max_category_time
       discretionary_limit = mct - discretionary_used()
       discretionary_limit, javascript_total = discretionary_limit_update(discretionary_limit, javascript_total, "javascript")
       discretionary_limit, ruby_total = discretionary_limit_update(discretionary_limit, ruby_total, "ruby")
       discretionary_limit, haskell_total = discretionary_limit_update(discretionary_limit, haskell_total, "haskell")
       # puts "@+@ practice_limits outgoing " + (javascript_total.to_s) + "," + ( ruby_total.to_s) + "," + (haskell_total.to_s)
       discretionary_total = mct - (discretionary_limit + discretionary_used())
       # puts "@+@ discretionary_total for previous week is " + (discretionary_total.to_s)
       return javascript_total, ruby_total, haskell_total, discretionary_total
    end

    def sum_practice(javascript_total, ruby_total, haskell_total, discretionary_total)
       sum_javascript_total = sum_up("javascript") + javascript_total
       sum_ruby_total = sum_up("ruby") + ruby_total
       sum_haskell_total = sum_up("haskell") + haskell_total
       sum_discretionary_total = discretionary_total + discretionary_used()
       return sum_javascript_total, sum_ruby_total, sum_haskell_total, sum_discretionary_total
    end

    def report(javascript_total, ruby_total, haskell_total, discretionary_total)
       return if @skip_report
       mct = @config.max_category_time
       # http://stackoverflow.com/questions/1359370/how-do-you-find-a-min-max-with-ruby
       javascript_total = @config.to_f([javascript_total, mct].min)
       ruby_total = @config.to_f([ruby_total, mct].min)
       haskell_total = @config.to_f([haskell_total, mct].min)
       discretionary_total = @config.to_f([discretionary_total, mct].min)
       puts "note: in each of following categories, can not exceed " + (mct.to_s) + " minutes"
       puts "@@ minutes of javascript to date = " + (javascript_total.to_s)
       puts "@@ minutes of ruby to date = " + (ruby_total.to_s)
       puts "@@ minutes of haskell to date = " + (haskell_total.to_s)
       puts "@@ minutes of discretionary practice to date = " + (discretionary_total.to_s)
    end
end



class SummaryConfig
  attr_accessor :date, :report_type, :max_entry_time, :max_week_time, :git_from_file, :old_marks_file, :max_category_time
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
      @date = "unknown_date"
    end
    if arg_list.any? { |value| /^oldmarks=/ =~ value } then
      oldmarks_arg = arg_list.find { |value| /^oldmarks=/ =~ value }
      @old_marks_file = oldmarks_arg.sub("oldmarks=","")
    else
      @old_marks_file = "markssofar"
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
    @max_category_time = 450.0
  end
  def to_f(value)
    value.to_f.round(@float_precision)
  end
  def putsq(message)
    puts message unless @quiet
  end
  def puterr(message)
    @error_out.puts message
    puts ">>Problem occurred while processing this stage of file>> " + message
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
