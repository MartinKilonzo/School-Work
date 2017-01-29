puts 'hello'

File.open('gitlog.txt', 'w') do |fileStream|
  log = `git log`
  puts log.length

  logLines = log.split(/commit (.){40}\n/)
  puts logLines.length
  puts logLines[2]
  fileStream.syswrite(log)
end
