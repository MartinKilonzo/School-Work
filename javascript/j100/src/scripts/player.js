/*global YT */
const iframe = require('https://www.youtube.com/iframe_api');

function initialize() {
  // // Update the controls on load
  // updateTimerDisplay();
  // updateProgressBar();
  //
  // // Clear any old interval.
  // clearInterval(time_update_interval);
  //
  // // Start interval to update elapsed time display and
  // // the elapsed part of the progress bar every second.
  // time_update_interval = setInterval(function () {
  //     updateTimerDisplay();
  //     updateProgressBar();
  // }, 1000)
}

var videoPlayer;

function onYouTubeIframeAPIReady() {
  videoPlayer = new YT.Player('video-placeholder', {
    videoId: 'LaSRU5m_7og',
    playerVars: {
      color: 'white',
      playlist: 'taJ60kskkns,FG0fTKAqZ5g,OElxUkigPUE,hgx7L9Nyfjw',
    },
    events: {
      onReady: initialize
    }
  });
}
