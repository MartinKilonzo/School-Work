'use strict';

const fs = require('fs');


const SYMBOLS = ['+', '-', '/', '*'];


const evaluate = (value1, action, value2) => {
  switch (action) {
    case '+': return value1 + value2;
      break;
    case '-': return value1 - value2;
      break;
    case '/': return value1 / value2;
      break;
    case '*': return value1 * value2;
      break;
    default: return null;
  }
};

const interpretLine = (line) => {
  const tList = [];
  line.split(/\b\s+\b/).forEach((t) => { // Apparently javascript doesnt support negative lookbehinds (?<!=)
    tList.push(t);
  });

  let hold;
  let action = -1;
  tList.forEach((t) => {
    if (t.match(/\d/)) {
      if (typeof hold !== 'undefined') {
        console.log('Parsing Error');
        return null;
      } else if (action === -1) {
        hold = t;
      } else {
        hold = evaluate(hold, action, t);
        action = -1;
      }
    } else if (t.match(/[+-/*]/)) {
      if (typeof hold === 'undefined') {
        console.log('Parsing Error');
        return null;
      }
      action = t;
    }
  });
  return hold; 
};


fs.readFile('./input.txt', 'utf8', (err, data) => {
  if (err) throw err;

  const tList = [];
  data.split(/[\n\r]+/).forEach((line) => {
    console.log(interpretLine(line));
  });
});
