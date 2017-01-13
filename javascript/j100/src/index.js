const player = document.createElement('script');
player.src = './src/player.js';

$(document).ready(() => {
  document.head.appendChild(player);
});
