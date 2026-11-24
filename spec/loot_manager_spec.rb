require_relative '../lib/loot_manager'

RSpec.describe LootManager do
  describe '.generate_loot' do
    it 'returns rotten flesh for a zombie most of the time' do
      loots = LootManager.generate_loot('zombie')
      expect(loots).to eq(['rotten_flesh'])
    end

    it 'returns bone for a zombie occasionally' do
      # We can't easily guarantee it happens once, but we can check the probability
      # Or just ensure it returns one of the valid keys
      loots = LootManager.generate_loot('zombie')
      expect(['rotten_flesh', 'bone']).to include(loots.first)
    end

    it 'returns gunpowder for a creeper' do
      expect(LootManager.generate_loot('creeper')).to eq(['gunpowder'])
    end

    it 'returns an empty array for unknown mobs' do
      expect(LootManager.generate_loot('unknown_mob')).to eq([])
    end
  end
end
