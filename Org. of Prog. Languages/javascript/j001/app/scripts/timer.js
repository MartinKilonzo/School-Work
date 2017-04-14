$('#app').load('../components/timer.html', () => {
  function Timer() {
    this.startTime = null;
    this.paused = true;
    this.timerComponent = $('#clock')[0];
    this.startButton = $('#clock').siblings('button').first();
    this.pauseButton = $('#clock').siblings('button').last();
    this.startButton.on('click', (event) => {
      this.startTimer();
    });
    this.pauseButton.on('click', (event) => {
      this.pauseTimer();
    });
    this.tick = () => {
      const time = new Date().getTime() - this.startTime;
      this.timerComponent.innerHTML = new Date(time).toLocaleTimeString();
    };

    this.startTimer = () => {
      this.paused = false;
      this.startTime = new Date().getTime();
      this.tick();
      this.interval = setInterval(() => { this.tick(); }, 57);
    };

    this.pauseTimer = () => {
      clearInterval(this.interval);
      this.startTime = new Date();
      if (this.paused) {
        this.startTimer();
      } else {
        this.paused = true;
      }
    };

    this.resetTimer = () => {
      this.pauseTimer();
      this.startTime = new Date();
    };
  }


  const timer = new Timer();
  timer.startTimer();
});
