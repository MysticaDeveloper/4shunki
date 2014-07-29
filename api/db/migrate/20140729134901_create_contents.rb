class CreateContents < ActiveRecord::Migration
  def change
    create_table :contents do |t|
      t.integer :score

      t.timestamps
    end
    add_attachment :contents, :file
  end
end
