/* globals examdatabase */

$(document).ready(() => {
  let questionIndex = -1;
  let correctQuestions = 0;
  let incorrectQuestions = 0;

  let correct = true;
  // Math.random.seed();
  const getRandomQuestion = () => {
    const min = 0;
    const max = examdatabase.questions.length;

    questionIndex = Math.floor(min + (Math.random() * (max - min)));
    $('#answer').text('');
  };

  const addCorrectQuestion = () => {
    console.log(correctQuestions)
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

  showNextQuestion();

  $('#correct').text(correctQuestions);
  $('#incorrect').text(incorrectQuestions);


  $('#question').siblings('button').on('click', event => showNextQuestion());
  $('#answer').siblings('button').on('click', event => showNextAnswer());
  $('#correctbtn').on('click', event => addCorrectQuestion());
});
