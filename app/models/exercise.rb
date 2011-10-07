class Exercise < ActiveRecord::Base
  
  belongs_to :course 
  belongs_to :user
  has_many :source_codes, :class_name => "SourceCode", :foreign_key => :exercise_id
  
end
