/* global YT */
const iframe = require('https://www.youtube.com/iframe_api');

var videoPlayer, playlist;

window.addEventListener('playlist', (event) => {
  console.log(event)
});


function onYouTubeIframeAPIReady() {
  videoPlayer = new YT.Player('video-placeholder', {
    videoId: 'LaSRU5m_7og',
    playerVars: {
      color: 'white',
      playlist: playlist,
    },
    loopPlaylists: true,
  });
}
