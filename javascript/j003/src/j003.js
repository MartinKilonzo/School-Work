/* globals examdatabase */

$(document).ready(() => {
  let questionIndex;
  let correctQuestions;
  let incorrectQuestions;

  let correct;

  // Math.random.seed();
  const getRandomQuestion = () => {
    const min = 0;
    const max = examdatabase.questions.length;

    questionIndex = Math.floor(min + (Math.random() * (max - min)));
    $('#answer').text('');
  };

  const addCorrectQuestion = () => {
    if (!correct) {
      correct = true;
      correctQuestions += 1;
      $('#correct').text(correctQuestions);
    }
  };

  const addIncorrectQuestion = () => {
    incorrectQuestions += 1;
    $('#incorrect').text(incorrectQuestions);
  };

  const showNextQuestion = () => {
    if (!correct) addIncorrectQuestion();
    correct = false;
    getRandomQuestion();
    const question = examdatabase.questions[questionIndex].question;
    $('#question').text(question);
  };

  const showNextAnswer = () => {
    const answer = examdatabase.questions[questionIndex].answer;
    $('#answer').text(answer);
  };

  const start = () => {
    questionIndex = -1;
    correctQuestions = 0;
    incorrectQuestions = 0;
    correct = true;
    $('#correct').text(correctQuestions);
    $('#incorrect').text(incorrectQuestions);
    showNextQuestion();
  };

  start();

  $('#question').siblings('button').on('click', event => showNextQuestion());
  $('#answer').siblings('button').on('click', event => showNextAnswer());
  $('#correctbtn').on('click', event => addCorrectQuestion());
  $('#restartbtn').on('click', event => start());
});
