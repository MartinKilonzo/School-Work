$('#playlistAdd').on('click', (event) => {
  const addToPlaylistEvent = new Event('playlist');
  addToPlaylistEvent.data = '';
  document.dispatchEvent(addToPlaylistEvent);
});
