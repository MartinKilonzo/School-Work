$('#playlistAdd').on('click', (event) => {
  const YOUTUBE_URL_BASE = 'https://www.youtube.com/watch?v=';

  const newVideo = $('#playlistField').val().replace(YOUTUBE_URL_BASE, '');

  if (newVideo.length === 0) {
    alert('Please enter a valid YouTube URL.');
    return;
  }

  const addToPlaylistEvent = new Event('playlist');
  addToPlaylistEvent.data = newVideo;
  document.dispatchEvent(addToPlaylistEvent);
});
