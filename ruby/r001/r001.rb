File.open('gitlog.txt', 'w') do |fileStream|

  # Get the git log and split it by commit (removing the commit line identifier)
  logLines = `git log`.split(/commit [\w\d]{40}\s/)

  # Remove all commits by Bob Webber
  logLines.delete_if { |log| log.include? 'Author: Bob Webber <webber@csd.uwo.ca>' }

  # If an argument has been specified, use that as a project filter for the logs
  if ARGV[0] != nil
    logLines.delete_if { |log| !log.include? ARGV[0] }
  end

  # Output the modified log in chronological ascending order
  logLines.reverse!.each {|log| fileStream.syswrite(log)}
  puts logLines

end
