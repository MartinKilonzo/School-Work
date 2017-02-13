const fs = require('fs');

const getType = (numStr) => {
  if (numStr.match(/\./)) return `f ${numStr}`;
  return `i ${numStr}`;
};

fs.readFile('./nums.txt', 'utf8', (err, data) => {
  if (err) throw err;

  const numList = [];
  data.split('\n').forEach((line) => {
    line.split(/(\s)+/).forEach((token) => {
      if (token && token.match(/\d/)) numList.push(getType(token));
    });
  });

  console.log(numList);
});
