require "date"

def getGitLog()
  ret = []
  tempList = `git log`.split(/\n(?=commit)/)
  tempList.each do |log|
    log.gsub!("\n\n, \n")
    log.gsub!(/\n\n\s{4}|\n\n/, "\n")
    logComponents = log.split("\n")
    practiceLog = Hash.new
    practiceLog[:commit] = logComponents[0][/[\w]{40}/]
    practiceLog[:author] = logComponents[1][/(?<=Author:\s).*/]
    puts logComponents
    puts DateTime.parse(logComponents[2][/(?<=Date:\s{3}).*/])
    practiceLog[:date] = DateTime.parse(logComponents[2][/(?<=Date:\s{3}).*/])
    practiceLog[:message] = logComponents[3..-1].join(" ")
    ret.push(practiceLog)
  end
  return ret
end

def isPracticeLog(log)
  return log[:message].match(/time [jhr]\d{3}/)
end

File.write('gitlog.txt', `git log`)

logList = getGitLog()


logList.delete_if { |log| !isPracticeLog(log) }

logList.sort_by! { |log| log[:date] }

File.write('gitlog.txt', logList)

puts logList
