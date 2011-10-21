class Course < ActiveRecord::Base
  
  has_many :exercises
  
end
