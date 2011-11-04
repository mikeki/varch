class CreateAlgorithms < ActiveRecord::Migration
  def self.up
    create_table :algorithms do |t|
      t.integer :type
      t.float :percentage
      t.integer :similarity_id

      t.timestamps
    end
  end

  def self.down
    drop_table :algorithms
  end
end
