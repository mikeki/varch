class AddExerciseToSimilarities < ActiveRecord::Migration
  def self.up
    add_column :similarities, :exercise_id, :integer
  end

  def self.down
    remove_column :similarities, :exercise
  end
end
