HTML_BASE = '<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title></title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.2/css/bootstrap.min.css" integrity="sha384-y3tfxAZXuh4HwSYylfB+J125MxIs6mR5FOHamPBG064zB+AFeWH94NdvaCBm8qnd" crossorigin="anonymous">
  </head>
  <body>
    <div id="app">

    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.2/js/bootstrap.min.js" integrity="sha384-vZ2WRJMwsjRMW/8U7i6PWi6AlO1L79snBrmgiDpgIWJ82z8eA5lenwvxbMV1PAh7" crossorigin="anonymous"></script>
  </body>
</html>'

HTML_APP = "id=\"app\">\n"

# TODO: Format HTML list into a table
# TODO: repalce newline chars in logListTOHTML with <br>
# TODO: Load HTML_BASE from file



class String
  def stringInject(baseString, iInsertion, insertionString)
    stringStart = self[0..iInsertion]
    stringEnd = self[iInsertion..-1]
    return stringStart + insertionString + stringEnd
  end
end



def logListToHTML(logList)
  html = "<ul>\n"

  logList.each do |log|
    # Clean up each log HTML
    listItem = "<li>\n" + "<p>\n" + log + "</p>\n" + "</li>\n"
    listItem.gsub! "\n\n", "\n"
    html += listItem
  end

  html += "</ul>\n"

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
    iInsertion = HTML_BASE.index(HTML_APP) + HTML_APP.length
    htmlFileStream.write(HTML_BASE.stringInject(HTML_BASE, iInsertion, logListToHTML(logList)))
  end


  # Output the modified log in chronological ascending order
  logList.reverse!.each {|log| fileStream.syswrite(log)}
  # puts logList

end
