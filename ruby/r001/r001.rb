def isTask(log)
  line = log.split("\n    ")[-1]
  return !line.nil? ? (line[0..3].include? 'time') : false
end

File.open('gitlog.txt', 'w') do |fileStream|

  # Get the git log and split it by commit (removing the commit line identifier)
  logList = `git log`.split(/commit [\w\d]{40}\s/)

  # Remove all commits by Bob Webber
  logList.delete_if { |log| log.include? 'Author: Bob Webber <webber@csd.uwo.ca>' }

  # If an argument has been specified, use that as a project filter for the logs
  if ARGV[0] != nil
    logList.delete_if { |log| !log.include? ARGV[0] }
  end

  # Remove all non-tasks
  logList.delete_if { |log| !isTask(log) }

  #TODO: Sort the list by task


  # Output the modified log in chronological ascending order
  logList.reverse!.each {|log| fileStream.syswrite(log)}
  puts logList

end
