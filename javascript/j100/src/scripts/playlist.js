$('#app').append('<button>+</button>').on('click', (event) => {
  const addToPlaylistEvent = new Event('playlist');
  addToPlaylistEvent.data = 'woooooooooooo';
  document.dispatchEvent(addToPlaylistEvent);
});
