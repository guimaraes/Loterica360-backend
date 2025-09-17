#!/usr/bin/env python3
"""
Script para gerar massa de dados de vendas de janeiro 2024 a setembro 2025
"""

import random
import datetime
from datetime import timedelta
import uuid

# Configurações
START_DATE = datetime.date(2024, 1, 1)
END_DATE = datetime.date(2025, 9, 30)

# Jogos e seus preços
JOGOS = {
    'Mega Sena': 4.50,
    'Quina': 2.00,
    'Lotomania': 2.50,
    'Lotofácil': 2.50,
    'Dupla Sena': 2.50,
    'Timemania': 3.00,
    'Dia de Sorte': 2.50,
    'Super Sete': 1.50
}

# Pesos para popularidade dos jogos
GAME_WEIGHTS = {
    'Lotofácil': 30,
    'Mega Sena': 20,
    'Quina': 15,
    'Dupla Sena': 10,
    'Timemania': 8,
    'Dia de Sorte': 7,
    'Lotomania': 5,
    'Super Sete': 5
}

# Caixas (1-5)
CAIXAS = list(range(1, 6))

# Usuários
USUARIOS = ['carlos', 'maria', 'joao', 'ana', 'pedro', 'admin']

def get_sales_count_for_date(date):
    """Determina quantas vendas fazer baseado no dia da semana e mês"""
    day_of_week = date.weekday()  # 0=Monday, 6=Sunday
    month = date.month
    
    base_sales = 8
    
    # Mais vendas em fins de semana
    if day_of_week in [5, 6]:  # Sábado e Domingo
        base_sales += random.randint(10, 20)
    
    # Mais vendas em dezembro
    if month == 12:
        base_sales += random.randint(5, 15)
    
    # Mais vendas no verão (Jan, Fev, Jun, Jul)
    if month in [1, 2, 6, 7]:
        base_sales += random.randint(3, 8)
    
    return base_sales + random.randint(0, 10)

def choose_weighted_game():
    """Escolhe um jogo baseado nos pesos"""
    games = list(GAME_WEIGHTS.keys())
    weights = list(GAME_WEIGHTS.values())
    return random.choices(games, weights=weights, k=1)[0]

def generate_sales_for_date(date):
    """Gera vendas para uma data específica"""
    sales_count = get_sales_count_for_date(date)
    sales = []
    
    for _ in range(sales_count):
        game = choose_weighted_game()
        price = JOGOS[game]
        
        # Quantidade baseada no dia da semana
        if date.weekday() in [5, 6]:  # Fim de semana
            quantity = random.randint(2, 10)
        elif date.month == 12:  # Dezembro
            quantity = random.randint(3, 13)
        else:
            quantity = random.randint(1, 6)
        
        total_value = price * quantity
        caixa = random.choice(CAIXAS)
        usuario = random.choice(USUARIOS)
        
        # Horário aleatório durante o dia
        hour = random.randint(8, 19)
        minute = random.randint(0, 59)
        timestamp = datetime.datetime.combine(date, datetime.time(hour, minute))
        
        sales.append({
            'game': game,
            'quantity': quantity,
            'total_value': total_value,
            'caixa': caixa,
            'usuario': usuario,
            'date': date,
            'timestamp': timestamp
        })
    
    return sales

def generate_sql_insert(sales):
    """Gera SQL INSERT para as vendas"""
    sql_lines = []
    sql_lines.append("-- Vendas geradas automaticamente")
    sql_lines.append("INSERT INTO venda_caixa (id, caixa_id, jogo_id, quantidade, valor_total, data_venda, usuario_id, criado_em) VALUES")
    
    values = []
    for sale in sales:
        game_id_var = f"@{sale['game'].lower().replace(' ', '_').replace('ã', 'a').replace('ç', 'c')}_id"
        caixa_id_var = f"@caixa_{sale['caixa']}_id"
        usuario_id_var = f"@{sale['usuario']}_id"
        
        value = f"(UUID(), {caixa_id_var}, {game_id_var}, {sale['quantity']}, {sale['total_value']:.2f}, '{sale['date']}', {usuario_id_var}, '{sale['timestamp']}')"
        values.append(value)
    
    # Dividir em chunks de 50 para evitar statements muito grandes
    chunk_size = 50
    for i in range(0, len(values), chunk_size):
        chunk = values[i:i+chunk_size]
        if i > 0:
            sql_lines.append("\nINSERT INTO venda_caixa (id, caixa_id, jogo_id, quantidade, valor_total, data_venda, usuario_id, criado_em) VALUES")
        sql_lines.append(',\n'.join(chunk) + ';')
    
    return '\n'.join(sql_lines)

def main():
    print("Gerando dados de vendas...")
    
    all_sales = []
    current_date = START_DATE
    
    # Gerar vendas para cada dia no período
    while current_date <= END_DATE:
        daily_sales = generate_sales_for_date(current_date)
        all_sales.extend(daily_sales)
        current_date += timedelta(days=1)
    
    print(f"Total de vendas geradas: {len(all_sales)}")
    
    # Gerar SQL
    sql_content = """-- Script para criar massa de dados de vendas de janeiro 2024 a setembro 2025
-- Vendas variadas com padrões realistas de loteria

-- Obter IDs dos jogos
SET @mega_sena_id = (SELECT id FROM jogo WHERE nome = 'Mega Sena' LIMIT 1);
SET @quina_id = (SELECT id FROM jogo WHERE nome = 'Quina' LIMIT 1);
SET @lotomania_id = (SELECT id FROM jogo WHERE nome = 'Lotomania' LIMIT 1);
SET @lotofacil_id = (SELECT id FROM jogo WHERE nome = 'Lotofácil' LIMIT 1);
SET @dupla_sena_id = (SELECT id FROM jogo WHERE nome = 'Dupla Sena' LIMIT 1);
SET @timemania_id = (SELECT id FROM jogo WHERE nome = 'Timemania' LIMIT 1);
SET @dia_de_sorte_id = (SELECT id FROM jogo WHERE nome = 'Dia de Sorte' LIMIT 1);
SET @super_sete_id = (SELECT id FROM jogo WHERE nome = 'Super Sete' LIMIT 1);

-- Obter IDs dos caixas
SET @caixa_1_id = (SELECT id FROM caixa WHERE numero = 1 LIMIT 1);
SET @caixa_2_id = (SELECT id FROM caixa WHERE numero = 2 LIMIT 1);
SET @caixa_3_id = (SELECT id FROM caixa WHERE numero = 3 LIMIT 1);
SET @caixa_4_id = (SELECT id FROM caixa WHERE numero = 4 LIMIT 1);
SET @caixa_5_id = (SELECT id FROM caixa WHERE numero = 5 LIMIT 1);

-- Obter IDs dos usuários
SET @admin_id = (SELECT id FROM usuario WHERE email = 'admin@loteria360.local' LIMIT 1);
SET @carlos_id = (SELECT id FROM usuario WHERE email = 'carlos@loteria360.local' LIMIT 1);
SET @maria_id = (SELECT id FROM usuario WHERE email = 'maria@loteria360.local' LIMIT 1);
SET @joao_id = (SELECT id FROM usuario WHERE email = 'joao@loteria360.local' LIMIT 1);
SET @ana_id = (SELECT id FROM usuario WHERE email = 'ana@loteria360.local' LIMIT 1);
SET @pedro_id = (SELECT id FROM usuario WHERE email = 'pedro@loteria360.local' LIMIT 1);

"""
    
    sql_content += generate_sql_insert(all_sales)
    
    # Escrever arquivo SQL
    with open('src/main/resources/db/migration/V7__generated_sales_data.sql', 'w', encoding='utf-8') as f:
        f.write(sql_content)
    
    print("Arquivo V7__generated_sales_data.sql criado com sucesso!")
    print(f"Período: {START_DATE} a {END_DATE}")
    print(f"Total de vendas: {len(all_sales)}")
    
    # Estatísticas por jogo
    game_stats = {}
    for sale in all_sales:
        game = sale['game']
        if game not in game_stats:
            game_stats[game] = {'count': 0, 'total': 0}
        game_stats[game]['count'] += sale['quantity']
        game_stats[game]['total'] += sale['total_value']
    
    print("\nEstatísticas por jogo:")
    for game, stats in sorted(game_stats.items()):
        print(f"  {game}: {stats['count']} bilhetes, R$ {stats['total']:.2f}")

if __name__ == "__main__":
    main()
