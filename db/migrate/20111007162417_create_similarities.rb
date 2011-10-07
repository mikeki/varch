class CreateSimilarities < ActiveRecord::Migration
  def self.up
    create_table :similarities do |t|
      t.integer :source_code1_id
      t.integer :source_code2_id
      t.float :similarity
      t.float :mar
      t.string :used_algorithm

      t.timestamps
    end
  end

  def self.down
    drop_table :similarities
  end
end
