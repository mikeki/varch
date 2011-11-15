module ApplicationHelper

  def importance_of_similarity(similarity, result)
    if result && result >= similarity.exercise.mar
      return "<span style='color:red;font-weight:bold;'>#{result}</span>".html_safe
    else
      return result
    end
  end

end
