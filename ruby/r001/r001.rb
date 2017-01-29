puts 'hello'

File.open('gitlog.txt', 'w') do |fileStream|

  logLines = `git log`.split(/commit [\w\d]{40}\s/)

  logLines.delete_if { |log| log.include? 'Author: Bob Webber <webber@csd.uwo.ca>' }

  logLines.reverse!.each {|log| fileStream.syswrite(log)}
end
