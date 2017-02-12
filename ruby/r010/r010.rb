def parseNumber(str)
  return str.split(/(\s)+/)
end

File.readlines('nums.txt').each do |line|
  numList = parseNumber(line)
  numList.delete_if { |num| !num.match(/\d/) }
  numList.map! {|num| 'i ' + num}
  puts numList
end
