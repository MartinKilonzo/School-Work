const fs = require('fs');

const PUNCTUATION = /[,;|()[\]{}]/;

const getType = (numStr) => {
  if (numStr.match(/[\d]+\.[\d]/)) return `f ${numStr}`;
  else if (numStr.match(PUNCTUATION)) return `s ${numStr}`;
  return `i ${numStr}`;
};

fs.readFile('./nums.txt', 'utf8', (err, data) => {
  if (err) throw err;

  const numList = [];
  data.split(/[\n\r]+/).forEach((line) => {
    line.split(/\b\s+\b(?!=)/).forEach((token) => { // Apparently javascript doesnt support negative lookbehinds (?<!=)
      // if (token && token.match(/[\d,;|()[\]{}]/)) numList.push(getType(token));
      if (token.match(/\.\d+/)) numList.push(`f ${token}`);
      else if (token.match(/[(),;[]|{}]/)) numList.push(`s ${token}`);
      else if (token.match(/=/)) numList.push(`v ${token}`);
      else if (token.match(/\d+/)) numList.push(`i ${token}`);
    });
  });

  console.log(numList);
});
