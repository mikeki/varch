class AddLanguageToSourceCodes < ActiveRecord::Migration
  def self.up
    add_column :source_codes, :language, :string
  end

  def self.down
    remove_column :source_codes, :language
  end
end
