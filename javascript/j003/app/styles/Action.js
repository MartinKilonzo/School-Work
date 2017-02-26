const Action = function Action(oldState, newState, applier, unapplier) {
  this.oldState = oldState;
  this.newState = newState;
  this.applier = applier;
  this.unapplier = unapplier;
};
Action.prototype.apply = function apply() {
  this.applier(this.newState);
};
Action.prototype.unapply = function unapply() {
  this.unapplier(this.oldState);
};
