require 'launchy'

class String
  def stringInject(baseString, iInsertion, insertionString)
    stringStart = self[0..iInsertion]
    stringEnd = self[iInsertion..-1]
    return stringStart + insertionString + stringEnd
  end
end

HTML_BASE = File.read('base.html')
HTML_APP = "    <div id=\"app\"></div>\n"


# TODO: Format HTML list into a table
# TODO: repalce newline chars in logListTOHTML with <br>
# TODO: make the page interactive by adding js --> freetime?


def strToTableData(dataList)
  puts dataList
  ret = "<tr>\n"
  dataList.each { |entry| ret += "<td>\n" + entry + "\n<td>\n" }
  ret += "</td>\n"
end



def logListToHTML(logList)
  html = "<table style=\"left:10vw; width:80vw\">\n"
  html += "<tr>\n<th>Date</th>\n<th>Author</th>\n<th>Task</th>\n<th>Time</th>\n<th>Message</th>\n</tr>"

  logList.each do |log|
    # Clean up each log HTML
    author = log[/Author: \w*/, 1]
    puts author
    date = log[/\w{3} \w{3} \d{2} \d{2}:\d{2}:\d{2} \d{4}/, 1]
    puts date
    task = log[/time [jrh]\d{4}/, 1][0..-1]
    puts task
    time = log[/time [jrh]\d{4} \d*/, 1][10..-1]
    puts time
    message = log[/time [jrh]\d{4} \d* .*/, 1][time.length..-1]
    puts message
    listItem = strToTableData([date, author, task, time, message])
    listItem.gsub! "\n\n", "\n"
    html += listItem
  end

  html += "</table>\n"

  return html
end



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

  File.open("index.html", 'w') do |htmlFileStream|
    puts HTML_BASE.index(HTML_APP)
    iInsertion = HTML_BASE.index(HTML_APP) + HTML_APP.length
    htmlFileStream.write(HTML_BASE.stringInject(HTML_BASE, iInsertion, logListToHTML(logList)))
  end


  # Output the modified log in chronological ascending order
  logList.reverse!.each {|log| fileStream.syswrite(log)}
  puts logList

  Launchy.open('./index.html')

end
