class Similarity < ActiveRecord::Base
  
  belongs_to :source_code1, :class_name=> "SourceCode", :foreign_key => :source_code1_id
  belongs_to :source_code2, :class_name=> "SourceCode", :foreign_key => :source_code2_id
  
end
