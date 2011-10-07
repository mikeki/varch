class CreateSourceCodes < ActiveRecord::Migration
  def self.up
    create_table :source_codes do |t|
      t.string :student_id
      t.integer :exercise_id

      t.timestamps
    end
  end

  def self.down
    drop_table :source_codes
  end
end
