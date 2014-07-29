class GeneralController < ApplicationController
  def index
    @images = ImageSearch.search(params[:query])

    respond_to do |format|
      format.html
      format.json {render json: @images}
    end
  end
end
