module LootManager
  # Defined weights for different mobs.
  # Format: { "mob_name" => [{ item: "id", weight: int }] }
  LOOT_TABLES = {
    "zombie" => [
      { item: "rotten_flesh", weight: 80 },
      { item: "bone", weight: 20 }
    ],
    "skeleton" => [
      { item: "arrow", weight: 80 },
      { item: "bone", weight: 20 }
    ],
    "creeper" => [
      { item: "gunpowder", weight: 100 }
    ],
    "spider" => [
      { item: "string", weight: 70 },
      { item: "spider_eye", weight: 30 }
    ]
  }.freeze

  # Returns an array of item IDs dropped by the killed mob.
  def self.generate_loot(mob_type)
    table = LOOT_TABLES[mob_type.to_s.downcase]
    return [] unless table

    total_weight = table.sum { |entry| entry[:weight] }
    return [] if total_weight == 0

    random_roll = rand(1..total_weight)

    current_weight = 0
    table.each do |entry|
      current_weight += entry[:weight]
      return [entry[:item]] if random_roll <= current_weight
    end

    []
  end

  def self.run_cli(mob_type)
    puts generate_loot(mob_type).join(",")
  end

  if __FILE__ == $0
    mob = ARGV[0] || "unknown"
    run_cli(mob)
  end
end
