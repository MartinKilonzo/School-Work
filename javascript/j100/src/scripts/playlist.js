$('#app').append('<button>+</button>').on('click', event => {
  addToPlaylistEvent = new Event('playlist');
  addToPlaylistEvent.data = 'woooooooooooo';
  document.dispatchEvent(addToPlaylistEvent);
});
