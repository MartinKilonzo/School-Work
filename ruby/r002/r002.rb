require "date"





def getGitLog()
  ret = []
  tempList = `git log`.split(/\n(?=commit)/)
  tempList.each do |log|
    log.gsub!("\n\n, \n")
    log.gsub!(/\n\n\s{4}|\n\n/, "\n")
    logComponents = log.split("\n")
    i = 0
    practiceLog = Hash.new
    practiceLog[:commit] = logComponents[i][/[\w]{40}/]
    if logComponents[i + 1].match(/(?<=Merge:\s).*/)
      i = 1
      practiceLog[:merge] = logComponents[i][/(?<=Merge:\s).*/]
    end
    practiceLog[:author] = logComponents[i + 1][/(?<=Author:\s).*/]
    # TODO: add a new field for merge
    practiceLog[:date] = DateTime.parse(logComponents[i + 2][/(?<=Date:\s{3}).*/])
    practiceLog[:message] = logComponents[i + 3..-1].join(" ")
    ret.push(practiceLog)
  end
  return ret
end



def isPracticeLog(log)
  return log[:message].match(/time [jhr]\d{3}/)
end



logList = getGitLog()

logList.delete_if { |log| !isPracticeLog(log) }

logList.sort_by! { |log| log[:date] }

puts logList[-1][:date].strftime("%a %b %d, %Y at %I:%M:%S %p")
