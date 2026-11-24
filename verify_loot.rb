require_relative 'lib/loot_manager'

puts "Testing Zombie..."
puts "Result: #{LootManager.generate_loot('zombie').inspect}"

puts "Testing Skeleton..."
puts "Result: #{LootManager.generate_loot('skeleton').inspect}"

puts "Testing Creeper..."
puts "Result: #{LootManager.generate_loot('creeper').inspect}"

puts "Testing Unknown..."
puts "Result: #{LootManager.generate_loot('ghost').inspect}"
