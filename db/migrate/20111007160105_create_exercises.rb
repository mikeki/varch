class CreateExercises < ActiveRecord::Migration
  def self.up
    create_table :exercises do |t|
      t.string :name
      t.text :description
      t.float :similarity_avarage
      t.float :mar
      t.integer :user_id
      t.integer :course_id

      t.timestamps
    end
  end

  def self.down
    drop_table :exercises
  end
end
