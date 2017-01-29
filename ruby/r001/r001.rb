puts 'hello'

File.open('gitlog.txt', 'w') do |fileStream|

  logLines = `git log`.split(/commit [\w\d]{40}\s/)

  puts logLines.length

  logLines.delete_if { |log| log.include? 'Author: Bob Webber <webber@csd.uwo.ca>' }

  puts logLines.length
  puts logLines
  # logLines.each {|log| puts log}
  logLines.each {|log| fileStream.syswrite(log)}
end
