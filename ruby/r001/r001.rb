puts 'hello';

File.open('gitlog.txt', 'w') do |fileStream|
  log = `git log`
  puts log.length
  fileStream.syswrite(log)
end
