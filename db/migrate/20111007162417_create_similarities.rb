class CreateSimilarities < ActiveRecord::Migration
  def self.up
    create_table :similarities do |t|
      t.integer :source_code1_id
      t.integer :source_code2_id
      t.float :algorithm_1
      t.float :algorithm_2
      t.float :algorithm_3

      t.timestamps
    end
  end

  def self.down
    drop_table :similarities
  end
end
