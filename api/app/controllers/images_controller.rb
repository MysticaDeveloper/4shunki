class ImagesController < ApplicationController
  def index
    @images = ImageSearch.search(params[:key])

    respond_to do |format|
      format.html
      format.json {render json: @images}
    end
  end
end
