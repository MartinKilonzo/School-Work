$('#app').load('../components/timer.html', () => {
  function Timer() {
    this.startTime = null;
    this.stopTime = 0;
    this.timePaused = 0;
    this.paused = true;
    this.timerComponent = $('#clock')[0];
    this.startButton = $('#clock').siblings('button').first();
    this.stopButton = $('#clock').siblings('button').first().next();
    this.pauseButton = $('#clock').siblings('button').last();
    this.startButton.on('click', (event) => {
      this.startTimer();
    });
    this.stopButton.on('click', (event) => {
      this.stopTimer();
    });
    this.pauseButton.on('click', (event) => {
      this.pauseTimer();
    });
    this.tick = () => {
      let newTime = new Date().getTime() - (this.startTime + this.stopTime);

      const milliseconds = newTime % 1000;
      newTime = Math.floor((newTime - milliseconds) / 1000);

      const seconds = newTime % 60;
      newTime = Math.floor((newTime - seconds) / 60);

      const minutes = newTime % 60;
      newTime = Math.floor((newTime - minutes) / 60);

      const hours = newTime;

      this.timerComponent.innerHTML = this.formatTime(milliseconds, seconds, minutes, hours);
    };

    this.formatTime = (milliseconds, seconds, minutes, hours) => {
      if (hours > 0) return `${hours}:${minutes}:${seconds}.${milliseconds}`;
      else if (minutes > 0) return `${minutes}:${seconds}.${milliseconds}`;
      else if (seconds > 0) return `${seconds}.${milliseconds}`;
      return `0.${milliseconds}`;
    };

    this.startTimer = () => {
      this.paused = false;
      this.startTime = new Date().getTime();
      this.tick();
      this.interval = setInterval(() => { this.tick(); }, 57);
      this.startButton.hide();
      this.stopButton.show();
    };

    this.stopTimer = () => {
      clearInterval(this.interval);
      this.paused = true;
      this.stopTime = 0;
      this.timePaused = 0;
      this.stopButton.hide();
      this.startButton.show();
    };

    this.pauseTimer = () => {
      clearInterval(this.interval);
      if (this.paused) {
        this.paused = !this.paused;
        this.stopTime += new Date().getTime() - this.timePaused;
        this.tick();
        this.interval = setInterval(() => { this.tick(); }, 57);
      } else {
        this.paused = true;
        this.timePaused = new Date().getTime();
      }
    };
  }


  const timer = new Timer();
  timer.startTimer();
});
