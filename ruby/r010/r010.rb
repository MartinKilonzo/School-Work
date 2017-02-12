def parseNumber(str)
  return str.split(/(\s)+/)
end

File.readlines('nums.txt').each do |line|
  numList = parseNumber(line)
  numList.delete_if { |strNum| !strNum.match(/\d/) }
  numList.map! do |strNum|
    if strNum.match('\.')
      'f ' + strNum
    else
      'i ' + strNum
    end
  end
  puts numList
end
