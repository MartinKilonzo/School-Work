function ActionStack() {
  this.undoStack = [];
  this.redoStack = [];
}
ActionStack.prototye.apply = function apply(action) {
  action.apply();
  this.undoStack.push(action);
  if (this.redoStack.length > 0) {
    this.redoStack = [];
  }
};
ActionStack.prototype.undo = function undo() {
  const action = this.undoStack.pop();
  action.unapply();
  this.redoStack.push(action);
};
ActionStack.prototype.redo = function redo() {
  const action = this.redoStack.pop();
  action.apply();
  this.redoStack.push(action);
};
