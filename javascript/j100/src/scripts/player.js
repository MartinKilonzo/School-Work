/* global YT */
const iframe = require('https://www.youtube.com/iframe_api');

let videoPlayer;
let playlist;

document.addEventListener('playlist', (event) => {
  console.log(event);
});


function onYouTubeIframeAPIReady() {
  videoPlayer = new YT.Player('video-placeholder', {
    videoId: 'LaSRU5m_7og',
    playerVars: {
      color: 'white',
      playlist,
    },
    loopPlaylists: true,
  });
}

require('playlist.js');
