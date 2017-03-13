require 'date'
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
    a = log[:author].split(" <")
    author = "<a href=\"mailto:#{a[-1][/.*(?=>)/]}\">#{a[0]}</a>"
    task = log[:message][/(?<=time\s)[jhr]\d{3}/]
    time = log[:message][/(?<=time\s[jhr]\d{3}\s)\d+/]
    message = log[:message][/(?<=time\s[jhr]\d{3}\s\d{2}).*/]

    html += "<tr class=\"#{task[0]}\"><td>#{log[:date].strftime("%a %b %d, %Y at %I:%M:%S %p")}</td><td>#{author}</td><td>#{task}</td><td>#{time}</td><td>#{message}</td></tr>"
  end

  html += "</table>\n"

  return html
end



def getGitLog()
  ret = []
  tempList = `git log`.split(/\n(?=commit)/)
  tempList.each do |log|
    log.gsub!("\n\n, \n")
    log.gsub!(/\n\n\s{4}|\n\n/, "\n")
    logComponents = log.split("\n")
    i = 0
    practiceLog = Hash.new
    practiceLog[:commit] = logComponents[i][/[\w]{40}/]
    if logComponents[i + 1].match(/(?<=Merge:\s).*/)
      i = 1
      practiceLog[:merge] = logComponents[i][/(?<=Merge:\s).*/]
    end
    practiceLog[:author] = logComponents[i + 1][/(?<=Author:\s).*/]
    # TODO: add a new field for merge
    practiceLog[:date] = DateTime.parse(logComponents[i + 2][/(?<=Date:\s{3}).*/])
    practiceLog[:message] = logComponents[i + 3..-1].join(" ")
    ret.push(practiceLog)
  end
  return ret
end



def isPracticeLog(log)
return log[:message].match(/time [jhr]\d{3}/)
end

File.open('gitlog.txt', 'w') do |fileStream|

  # Get the git log and split it by commit (removing the commit line identifier)
  logList = getGitLog()

  # Remove all commits by Bob Webber
  logList.delete_if { |log| log[:author].include? 'Author: Bob Webber <webber@csd.uwo.ca>' }

  # If an argument has been specified, use that as a project filter for the logs
  if ARGV[0] != nil
    logList.delete_if { |log| !log.include? ARGV[0] }
  end

  # Remove all non-tasks
  logList.delete_if { |log| !isPracticeLog(log) }

  #TODO: Sort the list by task
  logList.sort_by! { |log| log[:date] }
  # logList.sort_by! { |log| log[:message] }


  File.open("index.html", 'w') do |htmlFileStream|
    puts HTML_BASE.index(HTML_APP)
    iInsertion = HTML_BASE.index(HTML_APP) + HTML_APP.length
    htmlFileStream.write(HTML_BASE.stringInject(HTML_BASE, iInsertion, logListToHTML(logList)))
  end


  # Output the modified log in chronological ascending order
  logList.reverse!.each {|log| fileStream.syswrite(log)}
  # puts logList

  # Launchy.open('./index.html')

end
