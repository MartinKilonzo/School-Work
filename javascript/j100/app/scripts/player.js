/*global YT */
const iframe = require('https://www.youtube.com/iframe_api');

var videoPlayer;
const playlist = 'taJ60kskkns,FG0fTKAqZ5g,OElxUkigPUE,hgx7L9Nyfjw';

function onYouTubeIframeAPIReady() {
  videoPlayer = new YT.Player('video-placeholder', {
    videoId: 'LaSRU5m_7og',
    playerVars: {
      color: 'white',
      playlist: playlist,
    },
    loopPlaylists: true
  });
}
