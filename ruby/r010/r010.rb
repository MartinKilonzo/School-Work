def clean(line)
  # Remove and split at all spaces that are not surrounding a variable,
  strList = line.split(/(?<!=)\b\s+\b(?!=)/)
  return strList
end

File.readlines('nums.txt').each do |line|
  strList = clean(line)

  strList.map! do |str|
    if str.match('\.\d+')
      'f ' + str
    elsif str.match('[(),;\[\]|{}]')
      's ' + str
    elsif str.match('=')
      'v ' + str
    elsif str.match('\d+')
      'i ' + str
    end
  end
  # Remove all nilClass
  strList.compact!
  puts strList
end
