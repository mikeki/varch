class SourceCode < ActiveRecord::Base
  belongs_to :exercise, :class_name => "Exercise", :foreign_key => :exercise_id
 
  has_many :similarity1, :class_name => "Similarity", :foreign_key => :source_code1_id
  has_many :similarity2, :class_name => "Similarity", :foreign_key => :source_code2_id
  
end
