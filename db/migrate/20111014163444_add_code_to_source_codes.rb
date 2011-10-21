class AddCodeToSourceCodes < ActiveRecord::Migration
  def self.up
    add_column :source_codes, :code, :text
  end

  def self.down
    remove_column :source_codes, :code
  end
end
