package com.isranascimento.testutils

import com.isranascimento.networkdtos.hotels.AddressResponse
import com.isranascimento.networkdtos.hotels.AmenityResponse
import com.isranascimento.networkdtos.hotels.FeaturedItemResponse
import com.isranascimento.networkdtos.hotels.GalleryResponse
import com.isranascimento.networkdtos.hotels.GeolocationResponse
import com.isranascimento.networkdtos.hotels.HotelResponse
import com.isranascimento.networkdtos.hotels.HotelsResponse
import com.isranascimento.networkdtos.hotels.PriceResponse
import com.isranascimento.networkdtos.hotels.QuantityDescriptorsResponse


object ExpectedHotelsResponse {
    fun expectedResponse() = HotelsResponse(
        results = listOf(
            HotelResponse(
                sku = "NHU-7987-0-0-0-0",
                isHotel = true,
                category = "hotel",
                smallDescription = "Espaço planejado para o total bem estar dos hóspedes que procuram" +
                        " prazer e comodidade em uma estadia na Serra.",
                amenities = listOf(
                    AmenityResponse(name = "Bar", category = "Comida / Bebida"),
                    AmenityResponse(name = "Wifi", category = "Diversos")
                ),
                id = "AT7987",
                price = expectedPrice(),
                huFreeCancellation = true,
                image = "https://static.hotelurbano.com/reservas/prod0/7/7987/" +
                        "5d28d5cf6fa3c_pousada-aardvark-inn.jpg",
                name = "Pousada Aardvark Inn",
                url = "https://www.hurb.com/hoteis/gramado/pousada-aardvark-inn-7987",
                description = "Espaços charmosos, bons serviços, conforto nos detalhes e" +
                        " ambiente informalmente descontraído. \r\nCom atendimento personalizado " +
                        "feito por uma equipe de funcionários e gerenciamento familiar, buscamos " +
                        "oferecer constantemente um alto padrão de hospitalidade aliados a preços acessíveis.",
                stars = 3,
                gallery = expectedGallery(),
                address = expectedAddress(),
                tags = listOf("Serra Gaúcha"),
                quantityDescriptors = expectedQuantityDescriptor(),
                featuredItem = expectedFeaturedItem()
            )
        )
    )

    private fun expectedFeaturedItem() = FeaturedItemResponse(
        amenities = listOf("Amenidade de banho", "Aparelho TV"),
        name = "Standard Duplo",
        image = "https://static.hotelurbano.com/reservas/prodaccommodation/30607/58c8470" +
                "703a3d_pousada-aardvark-inn.jpg",
        description = "Para seu conforto e bem estar, todas as acomodações oferecem " +
                "frigobar, calefação central, televisores LCD com TV a Cabo e DVD, " +
                "secador de cabelo, roupas de cama em algodão, toalhas king size, ducha " +
                "pressurizada com aquecimento central, sabonetes e shampoos com aroma do campo, " +
                "facilidade de bebidas quentes. Será disponível um berço para a criança " +
                "na gratuidade como cortesia."
    )

    private fun expectedQuantityDescriptor() = QuantityDescriptorsResponse(
        maxChildren = 1,
        maxAdults = 2,
        maxFreeChildrenAge = 17
    )

    private fun expectedGallery() = listOf(
        GalleryResponse(
            description = "",
            url = "https://static.hotelurbano.com/reservas/prod0/7/7987/5d28d5cf6fa3c_" +
                    "pousada-aardvark-inn.jpg"
        ),
        GalleryResponse(
            description = "",
            url = "https://static.hotelurbano.com/reservas/prod0/7/7987/5d28d5d8bfd46_" +
                    "pousada-aardvark-inn.jpg"
        )
    )

    private fun expectedAddress() = AddressResponse(
        city = "Gramado",
        country = "Brasil",
        idAtlasCity = null,
        idAtlasCountry = null,
        idAtlasNeighborhood = null,
        idAtlasState = null,
        idCity = 7744,
        idCountry = 1,
        idState = 23,
        state = "Rio Grande do Sul",
        street = "Rua Mestre, 18",
        zipcode = "95670000",
        geolocation = GeolocationResponse(
            lat = -29.372126f,
            lon = -29.372126f
        ),
    )

    private fun expectedPrice() = PriceResponse(
        currency = "BRL",
        currencyOriginal = "BRL",
        currentPrice = 287.88,
        oldPrice = 0.toDouble(),
        sku = "NHU-7987-0-0-0-0",
        originalAmountPerDay = 0.toDouble(),
        amountPerDay = 287.88,
        amount = 287.88
    )
}