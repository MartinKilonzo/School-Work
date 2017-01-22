$('#app').load('../components/timer.html', () => {
  function Timer() {
    this.startTime = null;
    this.currentTime = null;
    this.interval = null;
    this.timerComponent = $('#timer')[0];
    this.hours = $('#hours');
    this.minutes = $('#minutes');
    this.seconds = $('#seconds');
    this.tick = () => {
      time = new Date();
      this.timerComponent.innerHTML = time.toLocaleTimeString();
    };

    this.startTimer = () => {
      this.tick();
      this.interval = setInterval(() => { this.tick(); }, 57);
    };

    this.pauseTimer = () => {
      this.interval.clearInterval();
    };

    this.resetTimer = () => {
      this.pauseTimer();
      this.startTime = this.currentTime;
    };
  }


  const timer = new Timer();
  timer.startTimer();
});
